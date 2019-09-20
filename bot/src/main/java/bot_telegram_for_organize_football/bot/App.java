package bot_telegram_for_organize_football.bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ApiContextInitializer.init();
        TelegramBotsApi botApi= new TelegramBotsApi();
        
        try {
        	botApi.registerBot(new Organize5FootballBot());
        } catch(TelegramApiException e) {
        	e.printStackTrace();
        }
    }
}
