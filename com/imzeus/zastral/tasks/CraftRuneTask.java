package com.imzeus.zastral.tasks;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;
import org.powerbot.script.rt6.Hud;

import com.imzeus.zastral.zAstral;
import com.imzeus.zastral.objects.Task;

public class CraftRuneTask extends Task {
	
	//script instance
	private zAstral script;

	public CraftRuneTask(ClientContext ctx, zAstral script) {
		super(ctx);
		this.script = script;
	}

	@Override
	public boolean activate() {
		return ctx.objects.select().id(script.getAltar()).within(script.getAltarDebug()).poll().inViewport()
					&& !ctx.backpack.select().id(script.getPureEss()).isEmpty();
	}

	@Override
	public void execute() {
		GameObject altar = ctx.objects.select().id(script.getAltar()).within(script.getAltarDebug()).nearest().poll();
		if(altar.valid()) {
			if(altar.inViewport()) {
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
				System.out.println("Attempting to interact with altar");
				altar.interact("Craft");
				Condition.wait(new Callable<Boolean>() {
					@Override
					public Boolean call() throws Exception {
						return ctx.players.local().animation() != 791;
					}
				}, Random.nextInt(350, 600), 2);
			} else if(!altar.inViewport()) {
				if(ctx.players.local().tile().distanceTo(altar) > 10) {
					System.out.println("Attempting to face altar");
					ctx.camera.turnTo(altar);
					ctx.movement.step(altar);
				}
			}
		}
	}

}
