import { Injectable } from '@angular/core';
import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { InventorySubLocationExtendedService } from 'app/entities/inventory-sub-location-extended/inventory-sub-location-extended.service';
import { InventorySubLocationExtendedComponent } from 'app/entities/inventory-sub-location-extended/inventory-sub-location-extended.component';
import { InventorySubLocationDetailExtendedComponent } from 'app/entities/inventory-sub-location-extended/inventory-sub-location-detail-extended.component';
import { InventorySubLocationUpdateExtendedComponent } from 'app/entities/inventory-sub-location-extended/inventory-sub-location-update-extended.component';
import { InventorySubLocationDeletePopupExtendedComponent } from 'app/entities/inventory-sub-location-extended/inventory-sub-location-delete-dialog-extended.component';
import { InventorySubLocationResolve } from 'app/entities/inventory-sub-location';

@Injectable({ providedIn: 'root' })
export class InventorySubLocationExtendedResolve extends InventorySubLocationResolve {
    constructor(service: InventorySubLocationExtendedService) {
        super(service);
    }
}

export const inventorySubLocationExtendedRoute: Routes = [
    {
        path: '',
        component: InventorySubLocationExtendedComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_INVENTORY_ADMIN', 'ROLE_INVENTORY_MANAGER'],
            pageTitle: 'InventorySubLocations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: InventorySubLocationDetailExtendedComponent,
        resolve: {
            inventorySubLocation: InventorySubLocationExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_INVENTORY_ADMIN', 'ROLE_INVENTORY_MANAGER'],
            pageTitle: 'InventorySubLocations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: InventorySubLocationUpdateExtendedComponent,
        resolve: {
            inventorySubLocation: InventorySubLocationExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_INVENTORY_ADMIN', 'ROLE_INVENTORY_MANAGER'],
            pageTitle: 'InventorySubLocations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: InventorySubLocationUpdateExtendedComponent,
        resolve: {
            inventorySubLocation: InventorySubLocationExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_INVENTORY_ADMIN', 'ROLE_INVENTORY_MANAGER'],
            pageTitle: 'InventorySubLocations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const inventorySubLocationPopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: InventorySubLocationDeletePopupExtendedComponent,
        resolve: {
            inventorySubLocation: InventorySubLocationExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_INVENTORY_ADMIN'],
            pageTitle: 'InventorySubLocations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
