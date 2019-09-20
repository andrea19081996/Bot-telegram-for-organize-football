package bot_telegram_for_organize_football.bot;

import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class Organize5FootballBot extends TelegramLongPollingBot{

	public String getBotUsername() {
		return "Organize5FootballBot";
	}

	public void onUpdateReceived(Update arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getBotToken() {
		/*token of the bot is in the private class*/
		return AuxiliarClass.token;
	}

}
