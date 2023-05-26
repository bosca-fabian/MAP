package NetworkService;

import NetworkService.EntitiesServiceInterfaces.IFriendRequestNetworkService;
import NetworkService.EntitiesServiceInterfaces.IFriendshipNetworkService;
import NetworkService.EntitiesServiceInterfaces.IMessagesNetworkService;
import NetworkService.EntitiesServiceInterfaces.IUserNetworkService;
import Utils.ServiceObserver;

public interface INetworkService extends IUserNetworkService,
        IFriendshipNetworkService,
        IMessagesNetworkService,
        IFriendRequestNetworkService,
        ServiceObserver {

}
