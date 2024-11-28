import client.ClientSideApplication;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SEARCHtest {

    @Test
    public void OneItemSearch () {
        ArrayList<String> Expected = new ArrayList<>();
        Expected.add("APPLE");

        ArrayList<String> appleInput = ClientSideApplication.getSearchResult("APPLE");
        ArrayList<String> appleLowerInput = ClientSideApplication.getSearchResult("apple");
        ArrayList<String> appleSpaceInput = ClientSideApplication.getSearchResult(" A PP L    E     ");
        ArrayList<String> appleMixinput = ClientSideApplication.getSearchResult("    Ap Pl e");

        assertEquals(Expected, appleInput);
        assertEquals(Expected, appleLowerInput);
        assertEquals(Expected, appleSpaceInput);
        assertEquals(Expected, appleMixinput);
    }

    @Test
    public void TwoItemSearch () {
        ArrayList<String> Expected = new ArrayList<>();
        Expected.add("PORK RAW");
        Expected.add("PORK COOKED");
        
        ArrayList<String> appleInput = ClientSideApplication.getSearchResult("PORK");
        ArrayList<String> appleLowerInput = ClientSideApplication.getSearchResult("pork");
        ArrayList<String> appleSpaceInput = ClientSideApplication.getSearchResult(" p or k");
        ArrayList<String> appleMixinput = ClientSideApplication.getSearchResult("    Po R k");

        assertEquals(Expected, appleInput);
        assertEquals(Expected, appleLowerInput);
        assertEquals(Expected, appleSpaceInput);
        assertEquals(Expected, appleMixinput);
    }

    @Test
    public void ManyItemSearch () {
        ArrayList<String> Expected = new ArrayList<>();

        ArrayList<String> appleInput = ClientSideApplication.getSearchResult("SAUSAGE");
        assertEquals(Expected, appleInput);
    }

}
