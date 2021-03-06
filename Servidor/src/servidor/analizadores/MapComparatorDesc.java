package servidor.analizadores;

import java.util.Comparator;
import java.util.HashMap;

/**
 *
 * @author erick
 */
public class MapComparatorDesc implements Comparator<HashMap<String,String>>{

    
    private final String key;

    public MapComparatorDesc(String key)
    {
        this.key = key;
    }

    @Override
    public int compare(HashMap<String, String> first,
                       HashMap<String, String> second)
    {
        // TODO: Null checking, both for maps and values
        String firstValue = first.get(key).trim();
        String secondValue = second.get(key).trim();
        int result= firstValue.compareTo(secondValue);
           if (result > 0){
            return -1;
        }else if (result < 0){
            return +1;
        }else{
            return 0;
        }
    }
    

    
    
}