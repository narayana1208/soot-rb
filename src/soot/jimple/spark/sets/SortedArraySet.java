/* Soot - a J*va Optimization Framework
 * Copyright (C) 2002 Ondrej Lhotak
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 */

package soot.jimple.spark.sets;
import soot.jimple.spark.*;
import soot.jimple.spark.pag.Node;
import soot.jimple.spark.pag.PAG;
import java.util.*;
import soot.Type;

/** Implementation of points-to set using a sorted array.
 * @author Ondrej Lhotak
 */
public class SortedArraySet extends PointsToSetInternal {
    public SortedArraySet( Type type, PAG pag ) {
        super( type );
    }
    /** Returns true if this set contains no run-time objects. */
    public boolean isEmpty() {
        return size == 0;
    }
    /** Adds contents of other into this set, returns true if this set 
     * changed. */
    public boolean addAll( final PointsToSetInternal other,
            final PointsToSetInternal exclude ) {
        boolean ret = false;
        if( other instanceof SortedArraySet ) {
            SortedArraySet o = (SortedArraySet) other;
            Node[] mya = nodes;
            Node[] oa = o.nodes;
            int osize = o.size;
            Node[] newa = new Node[ size + osize ];
            int myi = 0;
            int oi = 0;
            int newi = 0;
            for( ;; ) {
                if( myi < size ) {
                    if( oi < osize ) {
                        int myhc = System.identityHashCode( mya[myi] );
                        int ohc = System.identityHashCode( oa[oi] );
                        if( myhc < ohc ) {
                            newa[ newi++ ] = mya[ myi++ ];
                        } else if( myhc > ohc ) {
                            if( fh == null || type == null ||
                                fh.canStoreType( oa[ oi ].getType(), type ) ) {
                                newa[ newi++ ] = oa[ oi ];
                                ret = true;
                            }
                            oi++;
                        } else {
                            newa[ newi++ ] = mya[ myi++ ];
                            oi++;
                        }
                    } else { // oi >= osize
                        newa[ newi++ ] = mya[ myi++ ];
                    }
                } else { // myi >= size
                    if( oi < osize ) {
                        if( fh == null || type == null ||
                            fh.canStoreType( oa[ oi ].getType(), type ) ) {
                            newa[ newi++ ] = oa[ oi ];
                            ret = true;
                        }
                        oi++;
                    } else {
                        break;
                    }
                }
            }
            nodes = newa;
            return ret;
        }
        return super.addAll( other, exclude );
    }
    /** Calls v's visit method on all nodes in this set. */
    public boolean forall( P2SetVisitor v ) {
        for( int i = 0; i < size; i++ ) {
            v.visit( nodes[i] );
        }
        return v.getReturnValue();
    }
    /** Adds n to this set, returns true if n was not already in this set. */
    public boolean add( Node n ) {
        if( fh == null || type == null ||
            fh.canStoreType( n.getType(), type ) ) {

            if( contains(n) ) return false;
            int left = 0;
            int right = size;
            int mid;
            int hc = System.identityHashCode( n );
            while( left < right ) {
                mid = (left + right)/2;
                int midhc = System.identityHashCode( nodes[mid] );
                if( midhc < hc ) {
                    left = mid+1;
                } else if( midhc > hc ) {
                    right = mid;
                } else break;
            }
            if( size == nodes.length ) {
                Node[] newNodes = new Node[size+4];
                System.arraycopy( nodes, 0, newNodes, 0, nodes.length );
                nodes = newNodes;
            }
            System.arraycopy( nodes, left, nodes, left+1, size-left );
            nodes[left] = n;
            size++;
        }
        return false;
    }
    /** Returns true iff the set contains n. */
    public boolean contains( Node n ) {
        int left = 0;
        int right = size;
        int hc = System.identityHashCode( n );
        while( left < right ) {
            int mid = (left + right)/2;
            int midhc = System.identityHashCode( nodes[mid] );
            if( midhc < hc ) {
                left = mid+1;
            } else if( midhc > hc ) {
                right = mid;
            } else return true;
        }
        return false;
    }
    public static P2SetFactory getFactory() {
        return new P2SetFactory() {
            public PointsToSetInternal newSet( Type type, PAG pag ) {
                return new SortedArraySet( type, pag );
            }
        };
    }

    /* End of public methods. */
    /* End of package methods. */

    private Node[] nodes = null;
    private int size = 0;
}
