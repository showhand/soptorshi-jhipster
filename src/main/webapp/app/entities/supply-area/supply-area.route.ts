import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SupplyArea } from 'app/shared/model/supply-area.model';
import { SupplyAreaService } from './supply-area.service';
import { SupplyAreaComponent } from './supply-area.component';
import { SupplyAreaDetailComponent } from './supply-area-detail.component';
import { SupplyAreaUpdateComponent } from './supply-area-update.component';
import { SupplyAreaDeletePopupComponent } from './supply-area-delete-dialog.component';
import { ISupplyArea } from 'app/shared/model/supply-area.model';

@Injectable({ providedIn: 'root' })
export class SupplyAreaResolve implements Resolve<ISupplyArea> {
    constructor(private service: SupplyAreaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISupplyArea> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SupplyArea>) => response.ok),
                map((supplyArea: HttpResponse<SupplyArea>) => supplyArea.body)
            );
        }
        return of(new SupplyArea());
    }
}

export const supplyAreaRoute: Routes = [
    {
        path: '',
        component: SupplyAreaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyAreas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SupplyAreaDetailComponent,
        resolve: {
            supplyArea: SupplyAreaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyAreas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SupplyAreaUpdateComponent,
        resolve: {
            supplyArea: SupplyAreaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyAreas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SupplyAreaUpdateComponent,
        resolve: {
            supplyArea: SupplyAreaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyAreas'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const supplyAreaPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SupplyAreaDeletePopupComponent,
        resolve: {
            supplyArea: SupplyAreaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyAreas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
