import { Injectable } from '@angular/core';
import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { InventoryLocationExtendedService } from 'app/entities/inventory-location-extended/inventory-location-extended.service';
import { InventoryLocationExtendedComponent } from 'app/entities/inventory-location-extended/inventory-location-extended.component';
import { InventoryLocationDetailExtendedComponent } from 'app/entities/inventory-location-extended/inventory-location-detail-extended.component';
import { InventoryLocationUpdateExtendedComponent } from 'app/entities/inventory-location-extended/inventory-location-update-extended.component';
import { InventoryLocationDeletePopupExtendedComponent } from 'app/entities/inventory-location-extended/inventory-location-delete-dialog-extended.component';
import { InventoryLocationResolve } from 'app/entities/inventory-location';

@Injectable({ providedIn: 'root' })
export class InventoryLocationExtendedResolve extends InventoryLocationResolve {
    constructor(service: InventoryLocationExtendedService) {
        super(service);
    }
}

export const inventoryLocationExtendedRoute: Routes = [
    {
        path: '',
        component: InventoryLocationExtendedComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_INVENTORY_ADMIN', 'ROLE_INVENTORY_MANAGER'],
            pageTitle: 'InventoryLocations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: InventoryLocationDetailExtendedComponent,
        resolve: {
            inventoryLocation: InventoryLocationExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_INVENTORY_ADMIN', 'ROLE_INVENTORY_MANAGER'],
            pageTitle: 'InventoryLocations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: InventoryLocationUpdateExtendedComponent,
        resolve: {
            inventoryLocation: InventoryLocationExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_INVENTORY_ADMIN', 'ROLE_INVENTORY_MANAGER'],
            pageTitle: 'InventoryLocations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: InventoryLocationUpdateExtendedComponent,
        resolve: {
            inventoryLocation: InventoryLocationExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_INVENTORY_ADMIN', 'ROLE_INVENTORY_MANAGER'],
            pageTitle: 'InventoryLocations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const inventoryLocationPopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: InventoryLocationDeletePopupExtendedComponent,
        resolve: {
            inventoryLocation: InventoryLocationExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_INVENTORY_ADMIN'],
            pageTitle: 'InventoryLocations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
