import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { InventoryLocation } from 'app/shared/model/inventory-location.model';
import { IInventoryLocation } from 'app/shared/model/inventory-location.model';
import { InventoryLocationServiceExtended } from 'app/entities/inventory-location-extended/inventory-location.service.extended';
import { InventoryLocationDeletePopupComponentExtended } from 'app/entities/inventory-location-extended/inventory-location-delete-dialog.component.extended';
import { InventoryLocationComponentExtended } from 'app/entities/inventory-location-extended/inventory-location.component.extended';
import { InventoryLocationDetailComponentExtended } from 'app/entities/inventory-location-extended/inventory-location-detail.component.extended';
import { InventoryLocationUpdateComponentExtended } from 'app/entities/inventory-location-extended/inventory-location-update.component.extended';

@Injectable({ providedIn: 'root' })
export class InventoryLocationResolveExtended implements Resolve<IInventoryLocation> {
    constructor(private service: InventoryLocationServiceExtended) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IInventoryLocation> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<InventoryLocation>) => response.ok),
                map((inventoryLocation: HttpResponse<InventoryLocation>) => inventoryLocation.body)
            );
        }
        return of(new InventoryLocation());
    }
}

export const inventoryLocationRouteExtended: Routes = [
    {
        path: '',
        component: InventoryLocationComponentExtended,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InventoryLocations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: InventoryLocationDetailComponentExtended,
        resolve: {
            inventoryLocation: InventoryLocationResolveExtended
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InventoryLocations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: InventoryLocationUpdateComponentExtended,
        resolve: {
            inventoryLocation: InventoryLocationResolveExtended
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InventoryLocations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: InventoryLocationUpdateComponentExtended,
        resolve: {
            inventoryLocation: InventoryLocationResolveExtended
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InventoryLocations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const inventoryLocationPopupRouteExtended: Routes = [
    {
        path: ':id/delete',
        component: InventoryLocationDeletePopupComponentExtended,
        resolve: {
            inventoryLocation: InventoryLocationResolveExtended
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InventoryLocations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
