package com.scookie.brainscanner.device.bitalino;

public interface IDevice<K> {

    K getCommand(BitCommandArguments arguments);

}
