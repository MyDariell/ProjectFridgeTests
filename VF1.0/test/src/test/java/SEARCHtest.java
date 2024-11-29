import client.ClientSideApplication;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*

This is the items in the test file

APPLE, 5
PORK RAW, 35
PORK COOKED, 50
TINY SAUSAGE, 5
MEDIUM SAUSAGE, 10
LARGE SAUSAGE, 23
BOSNIAN SAUSAGE, 20
ASIAN SAUSAGE, 1
FETA CHEESE, 40

*/
public class SEARCHtest {

    @Test
    public void BlankInput(){
        ArrayList<String> Expected = new ArrayList<>();

        ArrayList<String> blank = ClientSideApplication.getSearchResult("");
        assertEquals(Expected, blank);
    }

    @Test
    public void OneItemSearch () {
        ArrayList<String> Expected = new ArrayList<>();
        Expected.add("APPLE");

        ArrayList<String> uppercase = ClientSideApplication.getSearchResult("APPLE");
        ArrayList<String> lowercase = ClientSideApplication.getSearchResult("apple");
        ArrayList<String> space = ClientSideApplication.getSearchResult("   APPLE");
        ArrayList<String> mixed = ClientSideApplication.getSearchResult("    ApPlE");

        assertEquals(Expected, uppercase);
        assertEquals(Expected, lowercase);
        assertEquals(Expected, space);
        assertEquals(Expected, mixed);
    }

    @Test
    public void OneItemSearchWithSpace () {
        ArrayList<String> Expected = new ArrayList<>();
        Expected.add("PORK RAW");

        ArrayList<String> uppercase = ClientSideApplication.getSearchResult("PORK RAW");
        ArrayList<String> lowercase = ClientSideApplication.getSearchResult("pork raw");
        ArrayList<String> space = ClientSideApplication.getSearchResult("   PORK RAW");
        ArrayList<String> mixed = ClientSideApplication.getSearchResult("    pork raW");

        assertEquals(Expected, uppercase);
        assertEquals(Expected, lowercase);
        assertEquals(Expected, space);
        assertEquals(Expected, mixed);
    }

    @Test
    public void TwoItemSearch () {
        ArrayList<String> Expected = new ArrayList<>();
        Expected.add("PORK RAW");
        Expected.add("PORK COOKED");

        ArrayList<String> uppercase = ClientSideApplication.getSearchResult("PORK");
        ArrayList<String> lowercase = ClientSideApplication.getSearchResult("pork");
        ArrayList<String> space = ClientSideApplication.getSearchResult("   PORK");
        ArrayList<String> mixed = ClientSideApplication.getSearchResult("   poRk");

        assertEquals(Expected, uppercase);
        assertEquals(Expected, lowercase);
        assertEquals(Expected, space);
        assertEquals(Expected, mixed);
    }

    @Test
    public void ManyItemSearch () {
        ArrayList<String> Expected = new ArrayList<>();
        Expected.add("TINY SAUSAGE");
        Expected.add("MEDIUM SAUSAGE");
        Expected.add("LARGE SAUSAGE");
        Expected.add("BOSNIAN SAUSAGE");
        Expected.add("ASIAN SAUSAGE");

        ArrayList<String> uppercase = ClientSideApplication.getSearchResult("SAUSAGE");
        ArrayList<String> lowercase = ClientSideApplication.getSearchResult("sausage");
        ArrayList<String> space = ClientSideApplication.getSearchResult("  SAUSAGE");
        ArrayList<String> mixed = ClientSideApplication.getSearchResult("  SAusagE");

        assertEquals(Expected, uppercase);
        assertEquals(Expected, lowercase);
        assertEquals(Expected, space);
        assertEquals(Expected, mixed);
    }

    @Test
    public void noMatches(){
        ArrayList<String> Expected = new ArrayList<>();
        ArrayList<String> invalidInput1 = ClientSideApplication.getSearchResult("CPEN221");
        ArrayList<String> invalidInput2 = ClientSideApplication.getSearchResult("WHAT DO YOU THINK MAKES SENSE");
        ArrayList<String> invalidInput3 = ClientSideApplication.getSearchResult("!   =");

        assertEquals(Expected, invalidInput1);
        assertEquals(Expected, invalidInput2);
        assertEquals(Expected, invalidInput3);
    }

    @Test
    public void PORKRAW () {
        ArrayList<String> Expected = new ArrayList<>();
        Expected.add("PORK RAW");

        ArrayList<String> uppercase = ClientSideApplication.getSearchResult("PORK RAW");

        assertEquals(Expected, uppercase);
    }

}
