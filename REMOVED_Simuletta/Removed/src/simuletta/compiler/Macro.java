/*
 * (CC) This work is licensed under a Creative Commons
 * Attribution 4.0 International License.
 *
 * You find a copy of the License on the following
 * page: https://creativecommons.org/licenses/by/4.0/
 */
package simuletta.compiler;


@SuppressWarnings("serial")
public class Macro extends Mnemonic {
	int nPar;

    public Macro(boolean visible,int nPar) {
		super(visible);
		this.nPar=nPar;
	}

	public void dump(String bx) {
    	//begin ref(link) ms;
        System.out.println(" Macro " + bx + ":");
        for(Object ms:this) {
        	if(ms instanceof MacroSymbol) ((MacroSymbol)ms).dump();
        	if(ms instanceof MacroParam) ((MacroParam)ms).dump();
        }
    }

}
