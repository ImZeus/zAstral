package com.imzeus.zastral;

import java.awt.Graphics;
import java.util.ArrayList;

import org.powerbot.script.Area;
import org.powerbot.script.MessageEvent;
import org.powerbot.script.MessageListener;
import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script.Manifest;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.TilePath;

import com.imzeus.zastral.objects.Task;
import com.imzeus.zastral.tasks.BankingTask;
import com.imzeus.zastral.tasks.CloseBankTask;
import com.imzeus.zastral.tasks.CraftRuneTask;
import com.imzeus.zastral.tasks.EmptyPouchTask;
import com.imzeus.zastral.tasks.FillPouchTask;
import com.imzeus.zastral.tasks.OpenBankTask;
import com.imzeus.zastral.tasks.TeleportTask;
import com.imzeus.zastral.tasks.WalkingTask;

@SuppressWarnings("unused")
@Manifest(name = "zAstral", description = "Crafts astrals")
public class zAstral extends PollingScript<ClientContext> implements PaintListener, MessageListener {

	//script constants
	private final int GIANT_POUCH = 5514;
	private final int LARGE_POUCH = 5512;
	private final int SMALL_POUCH = 5509;
	private final int PURE_ESS = 7936;
	private final int ASTRAL_RUNE = 9075;
	private final int LAW_RUNE = 563;
	private final int ASTRAL_ALTAR = 17010;
	private final int RC_ANIM = 791;
	
	private final int[] DEGRADED_POUCHES = {5515, 5513};
	private final int[] REQUIRED_STAVES = {1385, 0}; //mud staff & earth staff
	
	private final Area ALTAR_DEBUG = new Area(new Tile(2159, 3865), new Tile(2157, 3865), 
											  new Tile(2157, 3863), new Tile(2159, 3863));
	private final Area BANK_DEBUG = new Area(new Tile(2096, 3922), new Tile(2096, 3916), 
											 new Tile(2105, 3916), new Tile(2105, 3922));
	private final Area BOOTH_DEBUG = new Area(new Tile(2097, 3921), new Tile(2097, 3919),
											  new Tile(2099, 3919), new Tile(2099, 3921));
	private final TilePath ALTAR_PATH = new TilePath(this.ctx, new Tile[] {new Tile(2100, 3919, 0), new Tile(2102, 3913, 0),
											new Tile(2109, 3914, 0), new Tile(2114, 3911, 0),
											new Tile(2114, 3904, 0), new Tile(2113, 3897, 0),
											new Tile(2114, 3891, 0), new Tile(2116, 3886, 0),
											new Tile(2119, 3880, 0), new Tile(2125, 3877, 0),
											new Tile(2131, 3874, 0), new Tile(2135, 3872, 0),
											new Tile(2135, 3865, 0), new Tile(2135, 3858, 0),
											new Tile(2142, 3861, 0), new Tile(2149, 3863, 0),
											new Tile(2155, 3863, 0)});
	
	//variables
	private boolean filled_pouches = false;
	
	private ArrayList<Task> tasklist = new ArrayList<Task>();
	
	public void start() {
		System.out.println("Starting up");
		tasklist.add(new OpenBankTask(ctx, this));
		tasklist.add(new BankingTask(ctx, this));
		tasklist.add(new CloseBankTask(ctx, this));
		tasklist.add(new FillPouchTask(ctx, this));
		tasklist.add(new OpenBankTask(ctx, this));
		tasklist.add(new BankingTask(ctx, this));
		tasklist.add(new CloseBankTask(ctx, this));
		tasklist.add(new WalkingTask(ctx, this));
		tasklist.add(new CraftRuneTask(ctx, this));
		tasklist.add(new EmptyPouchTask(ctx, this));
		tasklist.add(new CraftRuneTask(ctx, this));
		tasklist.add(new TeleportTask(ctx, this));		
	}
	
	@Override
	public void messaged(MessageEvent e) {
		
	}

	@Override
	public void repaint(Graphics arg0) {
		
	}

	@Override
	public void poll() {
		for(Task task : tasklist) {
		    if(task.activate()) {
		    	System.out.println("Runtime:"+ this.getRuntime() + " " + task.getClass().getName());
		        task.execute();
		    }
		}
	}
	
	public int getGiantPouch() {
		return GIANT_POUCH;
	}
	
	public int getLargePouch() {
		return LARGE_POUCH;
	}
	
	public int getSmallPouch() {
		return SMALL_POUCH;
	}
	
	public int getPureEss() {
		return PURE_ESS;
	}
	
	public int getAltar() {
		return ASTRAL_ALTAR;
	}
	
	public Area getAltarDebug() {
		return ALTAR_DEBUG;
	}
	
	public Area getBankDebug() {
		return BANK_DEBUG;
	}
	
	public Area getBoothDebug() {
		return BOOTH_DEBUG;
	}
	
	public TilePath getWalkPath() {
		return ALTAR_PATH;
	}
	
	public int[] getDegradedPouches() {
		return DEGRADED_POUCHES;
	}
	
	public boolean hasFilledPouches() {
		return filled_pouches;
	}
	
	public void setFilledPouches(boolean b) {
		filled_pouches = b;
	}

}
