import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void hasQuit(){
        assertEquals("q", Main.searchPlayer("q"));
    }

    @Test
    public void playerNotFound(){
        assertEquals("No match found.",Main.searchPlayer("sdgsvshgdgsh"));
    }

    @Test
    public void getStats(){
        assertEquals("Pero Antić (2014-2015)\n" +
                    "2013-14 3.4\n" +
                    "2014-15 2.7\n", Main.searchPlayer("pero antic"));
    }
}