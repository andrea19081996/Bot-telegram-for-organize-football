package bot_telegram_for_organize_football.bot;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Organize5FootballBot extends TelegramLongPollingBot{

	public String getBotUsername() {
		return "Organize5FootballBot";
	}

	public void onUpdateReceived(Update update) {
		System.out.println("prova");
		
		if(update.hasMessage() && update.getMessage().hasText()) {
			String message_text= update.getMessage().getText();
			long chat_id= update.getMessage().getChatId();
			
			SendMessage message= new SendMessage();
			message.setChatId(chat_id);
			message.setText("prova");
			try {
				execute(message);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public String getBotToken() {
		/*token of the bot is in the private class*/
		return AuxiliarClass.token;
	}

}
