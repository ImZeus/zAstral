package com.imzeus.zastral.tasks;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;

import com.imzeus.zastral.zAstral;
import com.imzeus.zastral.objects.Task;

public class WalkingTask extends Task {
	
	//script instance
	private zAstral script;

	public WalkingTask(ClientContext ctx, zAstral script) {
		super(ctx);
		this.script = script;
	}

	@Override
	public boolean activate() {
		return script.hasFilledPouches()
					&& !ctx.backpack.select().id(script.getPureEss()).isEmpty()
						&& !ctx.objects.select().id(script.getAltar()).within(script.getAltarDebug()).poll().inViewport();
	}

	@Override
	public void execute() {
		if(!ctx.movement.running() && ctx.movement.energyLevel() > 30) {
			System.out.println("Attempting to set to running");
			ctx.movement.running(true);
		}
		System.out.println("Attempting to run to altar");
		script.getWalkPath().randomize(2, 2).traverse();
		Condition.wait(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return ctx.players.local().idle();
			}
		}, Random.nextInt(1000, 2000), 2);
	}

}
