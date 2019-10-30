import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IInventoryLocation, InventoryLocation } from 'app/shared/model/inventory-location.model';
import { InventoryLocationExtendedService } from 'app/entities/inventory-location-extended/inventory-location-extended.service';
import { InventoryLocationExtendedComponent } from 'app/entities/inventory-location-extended/inventory-location-extended.component';
import { InventoryLocationDetailExtendedComponent } from 'app/entities/inventory-location-extended/inventory-location-detail-extended.component';
import { InventoryLocationUpdateExtendedComponent } from 'app/entities/inventory-location-extended/inventory-location-update-extended.component';
import { InventoryLocationDeletePopupComponentExtended } from 'app/entities/inventory-location-extended/inventory-location-delete-dialog-extended.component';

@Injectable({ providedIn: 'root' })
export class InventoryLocationResolveExtended implements Resolve<IInventoryLocation> {
    constructor(private service: InventoryLocationExtendedService) {}

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

export const inventoryLocationExtendedRoute: Routes = [
    {
        path: '',
        component: InventoryLocationExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InventoryLocations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: InventoryLocationDetailExtendedComponent,
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
        component: InventoryLocationUpdateExtendedComponent,
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
        component: InventoryLocationUpdateExtendedComponent,
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
