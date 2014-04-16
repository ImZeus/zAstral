package com.imzeus.zastral.tasks;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Hud;

import com.imzeus.zastral.zAstral;
import com.imzeus.zastral.objects.Task;

public class FillPouchTask extends Task {
	
	//script instance
	private zAstral script;

	public FillPouchTask(ClientContext ctx, zAstral script) {
		super(ctx);
		this.script = script;
	}

	@Override
	public boolean activate() {
		return ctx.backpack.select().id(script.getPureEss()).count() >= 23
					&& !script.hasFilledPouches()
						&& script.getBankDebug().contains(ctx.players.local());
	}

	@Override
	public void execute() {
		if(!ctx.hud.opened(Hud.Window.BACKPACK)) {
			System.out.println("Attempting to open inventory");
			ctx.hud.open(Hud.Window.BACKPACK);
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return ctx.hud.open(Hud.Window.BACKPACK);
				}
			}, Random.nextInt(250,500), 1);
		}
		if(!ctx.backpack.select().id(script.getGiantPouch()).isEmpty()) {
			System.out.println("Attempting to fill giant pouch");
			ctx.backpack.select().id(script.getGiantPouch()).poll().interact("Fill");
		}
		if(!ctx.backpack.select().id(script.getLargePouch()).isEmpty()) {
			System.out.println("Attempting to fill large pouch");
			ctx.backpack.select().id(script.getLargePouch()).poll().interact("Fill");
		}
		if(!ctx.backpack.select().id(script.getSmallPouch()).isEmpty()) {
			System.out.println("Attempting to fill small pouch");
			ctx.backpack.select().id(script.getSmallPouch()).poll().interact("Fill");
		}
		script.setFilledPouches(true);
	}

}
