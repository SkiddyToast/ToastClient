package dev.toastmc.toastclient.impl.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.ArgumentType
import dev.toastmc.toastclient.api.command.Command
import dev.toastmc.toastclient.api.command.type.ModuleArgumentType
import dev.toastmc.toastclient.api.command.type.SettingArgumentType
import dev.toastmc.toastclient.api.command.type.SettingValueArgumentType
import dev.toastmc.toastclient.api.module.Module
import dev.toastmc.toastclient.api.setting.Setting
import dev.toastmc.toastclient.api.util.*
import net.minecraft.command.CommandSource
import net.minecraft.util.Formatting

object SettingsCommand : Command("set") {
    override fun register(dispatcher: CommandDispatcher<CommandSource>) {
        val moduleArgumentType = ModuleArgumentType.getModule()
        val settingArgumentType = SettingArgumentType.setting(moduleArgumentType, "module", 1)
        dispatcher register rootLiteral(label) {
            argument("module", moduleArgumentType){
                argument("setting", settingArgumentType) {
                    argument("value", SettingValueArgumentType.value(
                        settingArgumentType as ArgumentType<Setting<*>>,
                        "setting",
                        1
                    )) {
                        does {
                            val module = it.getArgument("module", Module::class.java) as Module
                            val setting = it.getArgument("setting", Setting::class.java) as Setting<*>
                            val stringValue = it.getArgument("value", String::class.java) as String
                            if(setting.setValueFromString(stringValue)){
                                val moduleName = "${Formatting.DARK_GRAY}[${Formatting.DARK_GREEN}${module.getName()}${Formatting.DARK_GRAY}]${Formatting.GRAY}"
                                val settingString = "${Formatting.GREEN}${setting.name}${Formatting.GRAY} to ${Formatting.GREEN.toString() + stringValue}"
                                message(lit("$prefix $moduleName Successfully set $settingString"))
                            }
                            0
                        }
                    }
                }
            }
            does{
                println("Something didn't go right.")
                0
            }
        }
    }
}