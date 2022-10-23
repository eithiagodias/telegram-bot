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
		SendResponse sendResponse;

		int m = 0;

		TelegramBot bot = new TelegramBot("");
		ClientManager clientManager = new ClientManager();

		while (true) {
			updatesResponse = bot.execute(new GetUpdates().limit(100).offset(m));

			List<Update> updates = updatesResponse.updates();
			for (Update update : updates) {

				m = update.updateId() + 1;

				String chatId = update.message().chat().id().toString();
				String msg = update.message().text();

				System.out.println("Mensagem recebida: " + msg);

				clientManager.addNewClient(new Client(chatId));
				baseResponse = bot.execute(new SendChatAction(chatId, ChatAction.typing.name()));

				if(baseResponse.isOk()) {

					boolean isEnd = Pattern.matches("^(?i)(sair)$|(?i)(parar)$", msg.toLowerCase());
					boolean isStartMsg = Pattern.matches("^(?i)(iniciar)$|(?i)(start)$", msg.toLowerCase());
					if(isStartMsg) {
						clientManager.updateClientState(chatId, Client.StateCurrent.STARTED);
					}

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

					if(clientManager.getClient(chatId).isStarted()) {
						bot.execute(new SendMessage(chatId, "\uD83D\uDCCD Escolha/Digite uma das opções abaixo."));
						bot.execute(new SendMessage(chatId, OptionManager.getMenu()));
						clientManager.updateClientState(chatId, Client.StateCurrent.WAITING_FOR_CLIENT_TO_CHOOSE_OPTION);
					}

					if(clientManager.getClient(chatId).isNotStarted()) {
						bot.execute(new SendMessage(chatId, "\uD83D\uDC4B\uD83C\uDFFD Olá, Seja muito bem-vindo(a)! \uD83E\uDD73"));
						bot.execute(new SendMessage(chatId, "ℹ Para começar digite: Iniciar"));
					}

					if(isEnd) {
						bot.execute(new SendMessage(chatId, "Obrigado por usar nosso bot! ❤"));
						clientManager.updateClientState(chatId, Client.StateCurrent.NOT_STARTED);
					}
				}
			}
		}
	}
}
