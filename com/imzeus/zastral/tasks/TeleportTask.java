package com.imzeus.zastral.tasks;

import org.powerbot.script.rt6.ClientContext;

import com.imzeus.zastral.zAstral;
import com.imzeus.zastral.objects.Task;

public class TeleportTask extends Task {

	//script instance
	private zAstral script;
	
	public TeleportTask(ClientContext ctx, zAstral script) {
		super(ctx);
		this.script = script;
	}

	@Override
	public boolean activate() {
		return ctx.backpack.select().id(script.getPureEss()).isEmpty()
					&&!script.hasFilledPouches()
						&& ctx.players.local().idle()
							&& ctx.objects.select().id(script.getAltar()).within(script.getAltarDebug()).nearest().poll().inViewport();
	}

	@Override
	public void execute() {
		//ctx.combatBar.getActionAt(11).select(true);
		if(ctx.widgets.component(137, 88).text().contains("Press Enter")) {
			//press button
			ctx.combatBar.actionAt(11).select(true);
			System.out.println("Player is not chatting.");
		} else {
			//click the combat bar
			ctx.combatBar.actionAt(11).select(false);
			System.out.println("Player is chatting.");
		}
	}
}
