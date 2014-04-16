package com.imzeus.zastral.tasks;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;

import com.imzeus.zastral.zAstral;
import com.imzeus.zastral.objects.Task;

public class BankingTask extends Task {
	
	//script instance
	private zAstral script;

	public BankingTask(ClientContext ctx, zAstral script) {
		super(ctx);
		this.script = script;
	}

	@Override
	public boolean activate() {
		return ctx.bank.open()
					&& ctx.backpack.select().id(script.getPureEss()).count() < 20;
	}

	@Override
	public void execute() {
		if(!ctx.backpack.select().id(script.getDegradedPouches()).isEmpty()) {
			for(int i : script.getDegradedPouches()) {
				if(!ctx.backpack.select().id(i).isEmpty()) {
					System.out.println("Depositing degraded pouch.");
					ctx.bank.deposit(i, 1);
				}
			}
		}
		System.out.println("Attempting to withdraw essence");
		ctx.bank.withdraw(script.getPureEss(), 0);
		Condition.wait(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return ctx.backpack.select().count() == 28;
			}
		}, Random.nextInt(350, 600), 2);
	}

}
