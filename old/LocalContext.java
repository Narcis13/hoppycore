/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seed;

/**
 *
 * @author Narcis
 */
public class LocalContext {
    int returnip;
	Object[] locals; // args + locals, indexed from 0

	public LocalContext(int returnip, int nlocals) {
		this.returnip = returnip;
		locals = new Object[nlocals];
	}
}
