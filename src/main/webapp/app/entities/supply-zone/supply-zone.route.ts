import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ISupplyZone, SupplyZone } from 'app/shared/model/supply-zone.model';
import { SupplyZoneService } from './supply-zone.service';
import { SupplyZoneComponent } from './supply-zone.component';
import { SupplyZoneDetailComponent } from './supply-zone-detail.component';
import { SupplyZoneUpdateComponent } from './supply-zone-update.component';
import { SupplyZoneDeletePopupComponent } from './supply-zone-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class SupplyZoneResolve implements Resolve<ISupplyZone> {
    constructor(private service: SupplyZoneService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISupplyZone> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SupplyZone>) => response.ok),
                map((supplyZone: HttpResponse<SupplyZone>) => supplyZone.body)
            );
        }
        return of(new SupplyZone());
    }
}

export const supplyZoneRoute: Routes = [
    {
        path: '',
        component: SupplyZoneComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyZones'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SupplyZoneDetailComponent,
        resolve: {
            supplyZone: SupplyZoneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyZones'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SupplyZoneUpdateComponent,
        resolve: {
            supplyZone: SupplyZoneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyZones'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SupplyZoneUpdateComponent,
        resolve: {
            supplyZone: SupplyZoneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyZones'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const supplyZonePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SupplyZoneDeletePopupComponent,
        resolve: {
            supplyZone: SupplyZoneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyZones'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
