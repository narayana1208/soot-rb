/* Soot - a J*va Optimization Framework
 * Copyright (C) 1999 Patrice Pominville, Raja Vallee-Rai
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 */

/*
 * Modified by the Sable Research Group and others 1997-1999.  
 * See the 'credits' file distributed with Soot for the complete list of
 * contributors.  (Soot is distributed at http://www.sable.mcgill.ca/soot)
 */


 



package soot.toolkits.graph;

import soot.*;
import soot.util.*;
import java.util.*;



/**
 *  Represents a CFG for a Body instance where the nodes are Unit
 *  instances, and where edges are added from statements inside an
 *  area of protection to the handler.
 *
 *  The difference between a TrapUnitGraph and a CompleteUnitGraph
 *  is that a CompleteUnitGraph has edges from the statement before
 *  the beginning of a try block to the handler, while a TrapUnitGraph
 *  does not.
 */
public class TrapUnitGraph extends UnitGraph
{

    /**
     *  Constructs the graph from a given Body instance.
     *  @param the Body instance from which the graph is built.
     */
    public TrapUnitGraph(Body body)
    {
        super(body, true, true);
    }

}

