import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { InventorySubLocation } from 'app/shared/model/inventory-sub-location.model';
import { InventorySubLocationService } from './inventory-sub-location.service';
import { InventorySubLocationComponent } from './inventory-sub-location.component';
import { InventorySubLocationDetailComponent } from './inventory-sub-location-detail.component';
import { InventorySubLocationUpdateComponent } from './inventory-sub-location-update.component';
import { InventorySubLocationDeletePopupComponent } from './inventory-sub-location-delete-dialog.component';
import { IInventorySubLocation } from 'app/shared/model/inventory-sub-location.model';

@Injectable({ providedIn: 'root' })
export class InventorySubLocationResolve implements Resolve<IInventorySubLocation> {
    constructor(private service: InventorySubLocationService) {}

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

export const inventorySubLocationRoute: Routes = [
    {
        path: '',
        component: InventorySubLocationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InventorySubLocations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: InventorySubLocationDetailComponent,
        resolve: {
            inventorySubLocation: InventorySubLocationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InventorySubLocations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: InventorySubLocationUpdateComponent,
        resolve: {
            inventorySubLocation: InventorySubLocationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InventorySubLocations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: InventorySubLocationUpdateComponent,
        resolve: {
            inventorySubLocation: InventorySubLocationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InventorySubLocations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const inventorySubLocationPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: InventorySubLocationDeletePopupComponent,
        resolve: {
            inventorySubLocation: InventorySubLocationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InventorySubLocations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
