package bec.compileTimeStack;

import java.util.HashMap;
import java.util.Vector;

import PREV.syntaxClass.instruction.RECORD;
import bec.util.Scode;

public class DataType {
//	Define tUnsigned=0,tSigned=1,tFloat=2;
	enum Kind { tUnsigned , tSigned , tFloat }
	
//	int nbyte;  // Size of type in bytes
	int size;  // Size of type in basic cellec
	Kind kind;  // tUnsigned,tSigned,tFloat
	Vector<Integer> pntmap; // NULL:no pointers, else: Reladdr of pointers

//    Infix(DataType) TTAB(MaxType); -- Type specification table
//	public static DataType[] TTAB = new DataType[32];
	private static HashMap<Integer, DataType> TMAP = new HashMap<Integer, DataType>();
	
	private DataType(int size, Kind kind) {
		this.size = size;
		this.kind = kind;
	}

	public static DataType dataType(int type) {
		DataType dt = TMAP.get(type);
		return dt;
	}

	public static int typeSize(int type) {
		DataType dt = TMAP.get(type);
		if(dt != null) return dt.size;
		return 1;
	}

	public static void initDataTypes() {
//	    -----------        type      size     kind            pnt  )
		newBasType( Scode.TAG_VOID    ,0     ,null                 );
		newBasType( Scode.TAG_INT     ,1     ,Kind.tSigned         );
		newBasType( Scode.TAG_REAL    ,1     ,Kind.tFloat          );
		newBasType( Scode.TAG_LREAL   ,1     ,Kind.tFloat          );
		newBasType( Scode.TAG_BOOL    ,1     ,Kind.tUnsigned       );
		newBasType( Scode.TAG_CHAR    ,1     ,Kind.tUnsigned       );
		newBasType( Scode.TAG_SIZE    ,1     ,Kind.tUnsigned       );
		newPntType( Scode.TAG_OADDR   ,1     ,Kind.tUnsigned ,0    );
		newBasType( Scode.TAG_AADDR   ,1     ,Kind.tUnsigned       );
		newPntType( Scode.TAG_GADDR   ,2     ,Kind.tUnsigned ,1    );
		newBasType( Scode.TAG_PADDR   ,4     ,Kind.tUnsigned       );
		newBasType( Scode.TAG_RADDR   ,4     ,Kind.tUnsigned       );
//		newBasType( Scode.TAG_NOINF   ,0     ,Kind.tUnsigned       );

	}

	public static void newRecType(RECORD rec) {
		DataType x = new DataType(rec.size(), null);
		TMAP.put(rec.tag, x);
	}

	public static void newRecType(int tag, int size) {
		DataType x = new DataType(size, null);
		TMAP.put(tag, x);
		System.out.println("DataType.newRecType: " + Scode.edTag(tag) + ", size="+size);
	}

	private static void newBasType(int type, int size, Kind kind) {
		DataType x = new DataType(size, kind);
		TMAP.put(type, x);
	}

	private static void newPntType(int type, int size, Kind kind, int rela) {
		DataType x = new DataType(size, kind);
	      x.pntmap = new Vector<Integer>();
	      x.pntmap.add(rela);
	}

}
