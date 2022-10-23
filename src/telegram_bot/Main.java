package telegram_bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;
import telegram_bot.api.GetInfoException;
import telegram_bot.entity.Client;
import telegram_bot.managers.ClientManager;
import telegram_bot.managers.OptionManager;

import java.util.List;
import java.util.regex.Pattern;

public class Main {

	public static void main(String[] args) {
		GetUpdatesResponse updatesResponse;
		BaseResponse baseResponse;

		int m = 0;

		TelegramBot bot = new TelegramBot("5668370444:AAGDK2RvU7GdvLpLsa9b7YVGp5Oq6-fEUoQ");
		ClientManager clientManager = new ClientManager();

		while (true) {
			updatesResponse = bot.execute(new GetUpdates().limit(100).offset(m));

			List<Update> updates = updatesResponse.updates();
			for (Update update : updates) {

				m = update.updateId() + 1;

				String chatId = update.message().chat().id().toString();
				String msg = update.message().text();

				System.out.println("Mensagem recebida: " + msg);

				// cria um novo client e adiciona ao gerenciador
				clientManager.addNewClient(new Client(chatId));
				baseResponse = bot.execute(new SendChatAction(chatId, ChatAction.typing.name()));

				if(baseResponse.isOk()) {

					// Identifica mensagem de sair
					boolean isEnd = Pattern.matches("^(?i)(sair)$|(?i)(parar)$", msg.toLowerCase());

					// Identifica mensagem de iniciar
					boolean isStartMsg = Pattern.matches("^(?i)(iniciar)$|(?i)(start)$", msg.toLowerCase());

					// Verifica se e uma mensagem para iniciar o bot
					if(isStartMsg) {
						clientManager.updateClientState(chatId, Client.StateCurrent.STARTED);
					}

					// Verifica se o estado do usuario esta aguardando que ele envie os parametros para consulta
					// e realiza a busca das infos e envia para o usuario
					if(clientManager.getClient(chatId).isWaitingSentParams()) {
						OptionManager.OPTIONS opt = clientManager.getClient(chatId).getOptionSelected();

						try {
							bot.execute(new SendMessage(chatId, "\uD83D\uDD75\uD83C\uDFFD\u200D♂ Buscando informações..."));
							String response = OptionManager.getResponseByOption(opt, msg);
							bot.execute(new SendMessage(chatId, response));
							clientManager.updateClientState(chatId, Client.StateCurrent.STARTED);
						} catch (GetInfoException e) {
							bot.execute(new SendMessage(chatId, "⛔ Erro ao buscar as informações. Por favor, revise os dados enviados e tente novamente."));
							bot.execute(new SendMessage(chatId, OptionManager.descriptionParams(opt)));
							clientManager.updateClientState(chatId, Client.StateCurrent.WAITING_FOR_SENT_PARAMS_BY_CLIENT);
						}

					}

					// Verifica se o estado do usuario esta aguardando que ele escolha um item no menu de opcoes
					if(clientManager.getClient(chatId).isWaitingChoose() && !isEnd) {
						OptionManager.OPTIONS option = OptionManager.getOption(msg);
						if(OptionManager.isValidOption(option)) {
							clientManager.updateClientOption(chatId, option);
							clientManager.updateClientState(chatId, Client.StateCurrent.WAITING_FOR_SENT_PARAMS_BY_CLIENT);
							bot.execute(new SendMessage(chatId, OptionManager.descriptionParams(option)));
						} else {
							bot.execute(new SendMessage(chatId, "⛔ Opção inválida. Tente novamente! "));
						}
					}

					// Verifica se o usuario iniciou o bot e exibe o menu para que ele escolha uma opcao
					if(clientManager.getClient(chatId).isStarted()) {
						bot.execute(new SendMessage(chatId, "\uD83D\uDCCD Escolha/Digite uma das opções abaixo."));
						bot.execute(new SendMessage(chatId, OptionManager.getMenu()));
						clientManager.updateClientState(chatId, Client.StateCurrent.WAITING_FOR_CLIENT_TO_CHOOSE_OPTION);
					}

					// Verifica se o usuario nao iniciou o bot e envia uma mensagem de boas vindas
					if(clientManager.getClient(chatId).isNotStarted()) {
						bot.execute(new SendMessage(chatId, "\uD83D\uDC4B\uD83C\uDFFD Olá, Seja muito bem-vindo(a)! \uD83E\uDD73"));
						bot.execute(new SendMessage(chatId, "ℹ Para começar digite: Iniciar"));
					}

					// Verifica se o usuario solicitou para sair do bot
					if(isEnd) {
						bot.execute(new SendMessage(chatId, "Obrigado por usar nosso bot! ❤"));
						clientManager.updateClientState(chatId, Client.StateCurrent.NOT_STARTED);
					}
				}
			}
		}
	}
}
