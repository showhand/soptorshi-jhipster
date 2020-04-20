import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ISupplyZoneManager, SupplyZoneManager } from 'app/shared/model/supply-zone-manager.model';
import { SupplyZoneManagerService } from './supply-zone-manager.service';
import { SupplyZoneManagerComponent } from './supply-zone-manager.component';
import { SupplyZoneManagerDetailComponent } from './supply-zone-manager-detail.component';
import { SupplyZoneManagerUpdateComponent } from './supply-zone-manager-update.component';
import { SupplyZoneManagerDeletePopupComponent } from './supply-zone-manager-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class SupplyZoneManagerResolve implements Resolve<ISupplyZoneManager> {
    constructor(private service: SupplyZoneManagerService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISupplyZoneManager> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SupplyZoneManager>) => response.ok),
                map((supplyZoneManager: HttpResponse<SupplyZoneManager>) => supplyZoneManager.body)
            );
        }
        return of(new SupplyZoneManager());
    }
}

export const supplyZoneManagerRoute: Routes = [
    {
        path: '',
        component: SupplyZoneManagerComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyZoneManagers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SupplyZoneManagerDetailComponent,
        resolve: {
            supplyZoneManager: SupplyZoneManagerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyZoneManagers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SupplyZoneManagerUpdateComponent,
        resolve: {
            supplyZoneManager: SupplyZoneManagerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyZoneManagers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SupplyZoneManagerUpdateComponent,
        resolve: {
            supplyZoneManager: SupplyZoneManagerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyZoneManagers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const supplyZoneManagerPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SupplyZoneManagerDeletePopupComponent,
        resolve: {
            supplyZoneManager: SupplyZoneManagerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyZoneManagers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
