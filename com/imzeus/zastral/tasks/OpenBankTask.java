package com.imzeus.zastral.tasks;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;

import com.imzeus.zastral.zAstral;
import com.imzeus.zastral.objects.Task;

public class OpenBankTask extends Task {
	
	//script instance
	private zAstral script;

	public OpenBankTask(ClientContext ctx, zAstral script) {
		super(ctx);
		this.script = script;
	}

	@Override
	public boolean activate() {
		System.out.println("Distance:" + script.getBankDebug().getClosestTo(ctx.players.local()).distanceTo(ctx.players.local()));
		return (ctx.backpack.select().id(script.getPureEss()).count() < 20
					&& !ctx.bank.opened()
						&& ctx.players.local().idle()
							&& (script.getBankDebug().contains(ctx.players.local())
								|| script.getBankDebug().getClosestTo(ctx.players.local()).distanceTo(ctx.players.local()) < 13));
	}

	@Override
	public void execute() {
		final GameObject banker = ctx.objects.select().id(16700).select().within(script.getBoothDebug()).nearest().poll();
		if(banker.valid()) {
			if(banker.inViewport()) {
				System.out.println("Attempting to open bank");
				System.out.println("Banker @ " + banker.tile());
				banker.interact("Bank");
				Condition.wait(new Callable<Boolean>() {
					@Override
					public Boolean call() throws Exception {
						return ctx.bank.opened();
					}
				}, Random.nextInt(350, 600), 2);
			} else if(!banker.inViewport()) {
				if(banker.tile().distanceTo(ctx.players.local().tile()) > 10) {
					System.out.println("Attempting to face banker");
					ctx.movement.step(banker);
					ctx.camera.turnTo(banker);
				} else {
					System.out.println("Failsafe stepping to banker");
					ctx.movement.step(banker);
					ctx.camera.turnTo(banker);
				}
			}
		} else {
			System.out.println("Failsafe stepping to banker");
			ctx.movement.step(script.getBankDebug().getRandomTile());
			ctx.camera.turnTo(script.getBankDebug().getRandomTile());
		}
	}

}
