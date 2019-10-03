package bot_telegram_for_organize_football.bot;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /*first test of a case booking of a person in an existing map*/
    
    public void testAppSimpleBookingCase() {
    	Map<Long,Set<String>> match= new HashMap<Long, Set<String>>();
    	Set<String> set1= new HashSet<String>();
    	set1.add("gianluca");
    	set1.add("mario");
    	set1.add("luca");
    	match.put(2147483649L, set1);
    	
    	SendMessage message= new SendMessage();
    	
    	String user="filippo";
    	
    	Organize5FootballBot.booking(2147483649L, message, user, match, "10/09/2019");
    	
    	Map<Long,Set<String>> match2= new HashMap<Long, Set<String>>();
    	Set<String> set2= new HashSet<String>();
    	set2.add("gianluca");
    	set2.add("mario");
    	set2.add("luca");
    	set2.add("filippo");
    	match2.put(2147483649L, set2);
    	
    	assertEquals(match, match2);
    }
    
    /*second test of a case booking of a person in an existing map
     * with more Long key in map*/
    
    public void testAppSimpleBookingCase2() {
    	Map<Long,Set<String>> match= new HashMap<Long, Set<String>>();
    	
    	/*first match*/
    	Set<String> set1= new HashSet<String>();
    	set1.add("gianluca");
    	set1.add("mario");
    	set1.add("luca");
    	match.put(2147483649L, set1);
    	
    	/*second match*/
    	Set<String> set2= new HashSet<String>();
    	set2.add("luca");
    	set2.add("mino");
    	set2.add("pino");
    	match.put(1147483649L, set2);
    	
    	/*third match*/
    	Set<String> set3= new HashSet<String>();
    	set3.add("andrea");
    	set3.add("daniele");
    	set3.add("francesco");
    	match.put(3147483649L, set3);
    	
    	SendMessage message= new SendMessage();
    	
    	String user="filippo";
    	
    	Organize5FootballBot.booking(1147483649L, message, user, match, "10/09/2019");
    	
    	Map<Long,Set<String>> match2= new HashMap<Long, Set<String>>();
    	Set<String> set4= new HashSet<String>();
    	set4.add("luca");
    	set4.add("mino");
    	set4.add("pino");
    	set4.add("filippo");
    	match2.put(1147483649L, set4);
    	
    	assertEquals(match.get(1147483649L), match2.get(1147483649L));
    }
    
    /* case booking of a person already in the list*/
    
    public void testAppFailureBookingCase() {
    	Map<Long,Set<String>> match= new HashMap<Long, Set<String>>();
    	Set<String> set1= new HashSet<String>();
    	set1.add("gianluca");
    	set1.add("mario");
    	set1.add("luca");
    	match.put(2147483649L, set1);
    	
    	SendMessage message= new SendMessage();
    	
    	String user="mario";
    	
    	Organize5FootballBot.booking(2147483649L, message, user, match, "10/09/2019");
    	
    	Map<Long,Set<String>> match2= new HashMap<Long, Set<String>>();
    	Set<String> set2= new HashSet<String>();
    	set2.add("gianluca");
    	set2.add("mario");
    	set2.add("luca");
    	match2.put(2147483649L, set2);
    	
    	assertEquals(match, match2);
    	assertEquals(message.getText(), "mario già sei nella lista");
    }
    
    /* case booking of last person for complete the match*/
    
    public void testAppCompleteBookingCase() {
    	Map<Long,Set<String>> match= new HashMap<Long, Set<String>>();
    	Set<String> set1= new HashSet<String>();
    	set1.add("gianluca");
    	set1.add("mario");
    	set1.add("luca");
    	set1.add("a");
    	set1.add("b");
    	set1.add("c");
    	set1.add("d");
    	set1.add("e");
    	set1.add("f");
    	match.put(2147483649L, set1);
    	
    	SendMessage message= new SendMessage();
    	
    	String user="andrea";
    	
    	Organize5FootballBot.booking(2147483649L, message, user, match, "10/09/2019");
    	
    	Map<Long,Set<String>> match2= new HashMap<Long, Set<String>>();
    	Set<String> set2= new HashSet<String>();
    	set2.add("gianluca");
    	set2.add("mario");
    	set2.add("luca");
    	set2.add("a");
    	set2.add("b");
    	set2.add("c");
    	set2.add("d");
    	set2.add("e");
    	set2.add("f");
    	set2.add("andrea");
    	match2.put(2147483649L, set2);
    	
    	assertEquals(match, match2);
    	System.out.println(message.getText());
    	assertEquals(message.getText(), "Partità per GIORNO alle ore XX: \na\nb\nc\nd\nandrea\nluca\ne\nf\ngianluca\nmario\n\nFormazione al completo");
    }
}
