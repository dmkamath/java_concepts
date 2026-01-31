import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
  public static void main(String[] args) {
    // inputMap={input.txt=Randy, output.txt=Randy, copy.py=Stan}
    Map<String, String> inputMap = getInputMap();
    System.out.println("inputMap="+inputMap);

    // expected outputMap={Randy=[input.txt, output.txt], Stan=[copy.py]}
    Map<String, List<String>> outputMap = getOutputMap(inputMap);

    System.out.println("outputMap=" + outputMap);
  }

  private static Map<String, String> getInputMap() {
    HashMap<String, String> map = new HashMap<>();
    map.put("input.txt", "Randy");
    map.put("copy.py", "Stan");
    map.put("output.txt", "Randy");
    return map;
  }

  private static Map<String, List<String>> getOutputMap(Map<String, String> inputMap) {
    Map<String, List<String>> outputMap = new HashMap<>();
    // TODO implement this method to return the desired output
    // expected outputMap={Randy=[input.txt, output.txt], Stan=[copy.py]}
    return outputMap;
  }

}
