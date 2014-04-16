package com.imzeus.zastral.tasks;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;

import com.imzeus.zastral.zAstral;
import com.imzeus.zastral.objects.Task;

public class CloseBankTask extends Task {
	
	//script instance
	private zAstral script;

	public CloseBankTask(ClientContext ctx, zAstral script) {
		super(ctx);
		this.script = script;
	}

	@Override
	public boolean activate() {
		return !ctx.backpack.select().id(script.getPureEss()).isEmpty()
					&& ctx.bank.open();
	}

	@Override
	public void execute() {
		System.out.println("Attempting to close bank");
		ctx.bank.close();
		Condition.wait(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return !ctx.bank.open();
			}
		}, Random.nextInt(350, 600), 2);
	}

}
