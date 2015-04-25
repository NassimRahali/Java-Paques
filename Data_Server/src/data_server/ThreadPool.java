/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_server;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 *
 * @author Kaoutare
 */
public class ThreadPool 
{
    private int taillePool;
    private int maxTaillePool;
    private int tempsLatence;
    private ThreadPoolExecutor poolThreads = null;
    private LinkedBlockingQueue<Runnable> queue;
    
    public ThreadPool(int t, int maxT, int tps)
    {
        taillePool = t;
        maxTaillePool = maxT;
        tempsLatence = tps;
        queue = new LinkedBlockingQueue<Runnable>();
        poolThreads = new ThreadPoolExecutor(taillePool,maxTaillePool,tempsLatence, TimeUnit.SECONDS,queue);
    }

    /**
     * @return the poolThreads
     */
    public ThreadPoolExecutor getPoolThreads() {
        return poolThreads;
    }

    /**
     * @param poolThreads the poolThreads to set
     */
    public void setPoolThreads(ThreadPoolExecutor poolThreads) {
        this.poolThreads = poolThreads;
    }
}
