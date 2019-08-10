import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { InventoryLocation } from 'app/shared/model/inventory-location.model';
import { InventoryLocationService } from './inventory-location.service';
import { InventoryLocationComponent } from './inventory-location.component';
import { InventoryLocationDetailComponent } from './inventory-location-detail.component';
import { InventoryLocationUpdateComponent } from './inventory-location-update.component';
import { InventoryLocationDeletePopupComponent } from './inventory-location-delete-dialog.component';
import { IInventoryLocation } from 'app/shared/model/inventory-location.model';

@Injectable({ providedIn: 'root' })
export class InventoryLocationResolve implements Resolve<IInventoryLocation> {
    constructor(private service: InventoryLocationService) {}

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

export const inventoryLocationRoute: Routes = [
    {
        path: '',
        component: InventoryLocationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InventoryLocations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: InventoryLocationDetailComponent,
        resolve: {
            inventoryLocation: InventoryLocationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InventoryLocations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: InventoryLocationUpdateComponent,
        resolve: {
            inventoryLocation: InventoryLocationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InventoryLocations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: InventoryLocationUpdateComponent,
        resolve: {
            inventoryLocation: InventoryLocationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InventoryLocations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const inventoryLocationPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: InventoryLocationDeletePopupComponent,
        resolve: {
            inventoryLocation: InventoryLocationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InventoryLocations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
