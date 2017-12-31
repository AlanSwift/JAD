import java.util.*;

public class Main {
    private HashMap<String,Integer> mp=new HashMap<>();
    public void work(Scanner in)
    {
        while(in.hasNext())
        {
            String line=in.nextLine();
            String[] fuck=line.split(" ");
            String url=fuck[6].replaceAll("\\?.*","");
            //System.out.println("fuck"+url);
            if(mp.containsKey(url))
            {
                Integer ff=mp.get(url);
                //System.out.println("uuuu: "+url+",  "+ff);
                ff++;
                mp.remove(url);
                mp.put(url,ff);

            }
            else{
                mp.put(url,1);
            }

        }
        int maxn=0;
        ArrayList<String> ans=new ArrayList<>();
        for(String key:mp.keySet())
        {
            //System.out.println("in map: "+furl+f);
            if(maxn<mp.get(key))
            {
                ans.clear();
                ans.add(key);
                maxn=mp.get(key);
            }
            else if(maxn==mp.get(key))
            {
                ans.add(key);
            }
        }
        Collections.sort(ans);
        for(String i:ans)
        {
            System.out.println(""+maxn+":"+i);
        }
    }

    public static void main(String[] args)
    {
        Scanner in =new Scanner(System.in);
        Main f=new Main();
        f.work(in);
    }
}
