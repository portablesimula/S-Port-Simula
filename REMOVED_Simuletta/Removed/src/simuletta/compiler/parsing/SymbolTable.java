package simuletta.compiler.parsing;

import java.util.Vector;

public class SymbolTable {
	private SymbolChain hashTab[]=new SymbolChain[256]; //(0:255);
	private Vector<String> nameTab=new Vector<String>();

	@SuppressWarnings("serial")
	private class SymbolChain extends Vector<Element> {	}
	
	private class Element {
		String symbolText;
		int symbolIndex;
	}
	
	public String getSymbol(int idx) {
		return(nameTab.elementAt(idx));
	}
	
	public int lookup(String txt) {
		int h=hash(txt);
		SymbolChain chain=hashTab[h];
		if(chain==null) { hashTab[h]=new SymbolChain();	return(-1); }
		for(Element e:chain) if(txt.equals(e.symbolText)) return(e.symbolIndex);
		return(-1);
	}
	
	public int defSymbol(String txt) {
		int idx=lookup(txt); if(idx >= 0) return(idx);
		SymbolChain chain=hashTab[hash(txt)];
		Element elt=new Element();
		elt.symbolText=txt;
		elt.symbolIndex=nameTab.size();
		nameTab.add(txt);
		chain.add(elt);
		return(elt.symbolIndex);
	}
	
	public int size() {
		return(nameTab.size());
	}
	
	public void print(String title) {
		System.out.println("======================="+title+"=======================");
		for(int i=0;i<nameTab.size();i++) {
			System.out.println("Symbol "+i+": \""+getSymbol(i)+"\"");
		}
	}

    private static int hash(final String txt) {
        int h = txt.hashCode();
        h ^= (h >>> 20) ^ (h >>> 12) ^ (h >>> 7) ^ (h >>> 4);
        return(h & 255);
    }

	public static void main(String[] args) {
		SymbolTable symTab=new SymbolTable();
		symTab.defSymbol("abra");
		symTab.defSymbol("ca");
		symTab.defSymbol("dab");
		symTab.print("Symbol Table");
		symTab.defSymbol("ca");
		symTab.defSymbol("dab");
		symTab.defSymbol("ski");
		symTab.print("Symbol Table");
	}
}
