package soot.dava.internal.javaRep;

import java.util.*;
import soot.jimple.internal.*;
import soot.dava.internal.SET.*;

public class DAbruptStmt extends AbstractStmt
{
    private String command;
    private SETNodeLabel label;


    public  boolean surpressDestinationLabel;

    public DAbruptStmt( String command, SETNodeLabel label)
    {
	this.command = command;
	this.label = label;

	label.set_Name();
	surpressDestinationLabel = false;
    }

    public boolean fallsThrough()
    {
	return false;
    }

    public boolean branches()
    {
	return false;
    }

    public Object clone()
    {
	return new DAbruptStmt( command, label);
    }

    public String toString( Map stmtToName, String indentation)
    {
	StringBuffer b = new StringBuffer();

	b.append( indentation);
	b.append( command);

	if ((surpressDestinationLabel == false) && (label.toString() != null)) {
	    b.append( " ");
	    b.append( label.toString());
	}

	return b.toString();
    }

    public String toString( boolean isBrief, Map stmtToName, String indentation)
    {
	return toString( stmtToName, indentation);
    }
    
    public boolean is_Continue()
    {
	return command.equals( "continue");
    }
}