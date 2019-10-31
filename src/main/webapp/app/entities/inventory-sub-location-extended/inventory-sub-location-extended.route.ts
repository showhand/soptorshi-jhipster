import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IInventorySubLocation, InventorySubLocation } from 'app/shared/model/inventory-sub-location.model';
import { InventorySubLocationExtendedService } from 'app/entities/inventory-sub-location-extended/inventory-sub-location-extended.service';
import { InventorySubLocationExtendedComponent } from 'app/entities/inventory-sub-location-extended/inventory-sub-location-extended.component';
import { InventorySubLocationDetailExtendedComponent } from 'app/entities/inventory-sub-location-extended/inventory-sub-location-detail-extended.component';
import { InventorySubLocationUpdateExtendedComponent } from 'app/entities/inventory-sub-location-extended/inventory-sub-location-update-extended.component';
import { InventorySubLocationDeletePopupExtendedComponent } from 'app/entities/inventory-sub-location-extended/inventory-sub-location-delete-dialog-extended.component';

@Injectable({ providedIn: 'root' })
export class InventorySubLocationExtendedResolve implements Resolve<IInventorySubLocation> {
    constructor(private service: InventorySubLocationExtendedService) {}

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

export const inventorySubLocationExtendedRoute: Routes = [
    {
        path: '',
        component: InventorySubLocationExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
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
            authorities: ['ROLE_USER'],
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
            authorities: ['ROLE_USER'],
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
            authorities: ['ROLE_USER'],
            pageTitle: 'InventorySubLocations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const inventorySubLocationPopupRouteExtended: Routes = [
    {
        path: ':id/delete',
        component: InventorySubLocationDeletePopupExtendedComponent,
        resolve: {
            inventorySubLocation: InventorySubLocationExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InventorySubLocations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
