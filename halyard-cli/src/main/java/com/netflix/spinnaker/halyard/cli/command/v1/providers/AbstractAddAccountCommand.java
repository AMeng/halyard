/*
 * Copyright 2016 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netflix.spinnaker.halyard.cli.command.v1.providers;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.netflix.spinnaker.halyard.cli.command.v1.NestableCommand;
import com.netflix.spinnaker.halyard.cli.services.v1.Daemon;
import com.netflix.spinnaker.halyard.config.model.v1.node.Account;
import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;

@Parameters()
public abstract class AbstractAddAccountCommand extends AbstractProviderCommand {
  @Getter(AccessLevel.PROTECTED)
  private Map<String, NestableCommand> subcommands = new HashMap<>();

  @Getter(AccessLevel.PUBLIC)
  private String commandName = "add-account";

  @Parameter(names = { "--account-name" }, description = "The name of the account to create")
  private String accountName;

  protected abstract Account buildAccount(String accountName);

  public String getDescription() {
    return "Add a " + getProviderName() + " account";
  }

  @Override
  protected void executeThis() {
    Account account = buildAccount(accountName);
    String providerName = getProviderName();

    String currentDeployment = Daemon.getCurrentDeployment();
    Daemon.addAccount(currentDeployment, providerName, !noValidate, account);
  }
}