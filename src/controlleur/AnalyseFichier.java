package controlleur;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalyseFichier
{
	/**
	 * Permet de recupérer une commande
	 * Exemple: 44D,3F,5A -> 44 medoc D, 3 medoc F et 5 medoc A
	 * @param commande
	 * @return
	 */
	public static Map<String, Integer> creerCommande(String commande)
	{
		Map<String, Integer> retour = new HashMap<String, Integer>();
		String regex = "^([1-9]*[A-Z]*,)*[1-9]*[A-Z]*,?$", code;
		String[] listeMedoc;
		Pattern pattern = Pattern.compile("^([1-9]*)([A-Z]*)$");
		Matcher matcher;
		int nombre;
		if(Pattern.matches(regex, commande) == true)
		{
			listeMedoc = commande.split(",");
			for(int i = 0; i < listeMedoc.length; i++)
			{
				matcher = pattern.matcher(listeMedoc[i]);
				if(matcher.matches())
				{
					nombre = Integer.parseInt(matcher.group(1));
					if(nombre > 0)
					{
						code = matcher.group(2);
						retour.put(code, nombre);
					}
				}
			}
		}
		return retour;
	}
	
	/**
	 * Ce sont des tests
	 * @param args
	 */
	public static void main(String[] args) 
	{
		System.out.println("Debut");
		 Map<String, Integer> titi;
		 Set<String> keys;
		 Iterator<String> iterator;
		 String commande;
		 
		 commande = "44D,3F,5A,";
		 titi = creerCommande(commande);
		 keys = titi.keySet();
		 iterator = keys.iterator();
		 System.out.println(commande);
		 while(iterator.hasNext())
		 {
			 String key = iterator.next();
			 System.out.println(titi.get(key) + " -> " + key);
		 }
		 
		 commande = "44D3F,5A,";
		 titi = creerCommande(commande);
		 keys = titi.keySet();
		 iterator = keys.iterator();
		 System.out.println(commande);
		 while(iterator.hasNext())
		 {
			 String key = iterator.next();
			 System.out.println(titi.get(key) + " -> " + key);
		 }
		 
		 commande = "44D,3F,5A";
		 titi = creerCommande(commande);
		 keys = titi.keySet();
		 iterator = keys.iterator();
		 System.out.println(commande);
		 while(iterator.hasNext())
		 {
			 String key = iterator.next();
			 System.out.println(titi.get(key) + " -> " + key);
		 }
	}
}
