import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { InventorySubLocation } from 'app/shared/model/inventory-sub-location.model';
import { IInventorySubLocation } from 'app/shared/model/inventory-sub-location.model';
import { InventorySubLocationServiceExtended } from 'app/entities/inventory-sub-location-extended/inventory-sub-location.service.extended';
import { InventorySubLocationComponentExtended } from 'app/entities/inventory-sub-location-extended/inventory-sub-location.component.extended';
import { InventorySubLocationDetailComponentExtended } from 'app/entities/inventory-sub-location-extended/inventory-sub-location-detail.component.extended';
import { InventorySubLocationUpdateComponentExtended } from 'app/entities/inventory-sub-location-extended/inventory-sub-location-update.component.extended';
import { InventorySubLocationDeletePopupComponentExtended } from 'app/entities/inventory-sub-location-extended/inventory-sub-location-delete-dialog.component.extended';

@Injectable({ providedIn: 'root' })
export class InventorySubLocationResolveExtended implements Resolve<IInventorySubLocation> {
    constructor(private service: InventorySubLocationServiceExtended) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IInventorySubLocation> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<InventorySubLocation>) => response.ok),
                map((inventorySubLocation: HttpResponse<InventorySubLocation>) => inventorySubLocation.body)
            );
        }
        return of(new InventorySubLocation());
    }
}

export const inventorySubLocationRouteExtended: Routes = [
    {
        path: '',
        component: InventorySubLocationComponentExtended,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InventorySubLocations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: InventorySubLocationDetailComponentExtended,
        resolve: {
            inventorySubLocation: InventorySubLocationResolveExtended
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InventorySubLocations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: InventorySubLocationUpdateComponentExtended,
        resolve: {
            inventorySubLocation: InventorySubLocationResolveExtended
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InventorySubLocations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: InventorySubLocationUpdateComponentExtended,
        resolve: {
            inventorySubLocation: InventorySubLocationResolveExtended
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InventorySubLocations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const inventorySubLocationPopupRouteExtended: Routes = [
    {
        path: ':id/delete',
        component: InventorySubLocationDeletePopupComponentExtended,
        resolve: {
            inventorySubLocation: InventorySubLocationResolveExtended
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InventorySubLocations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
