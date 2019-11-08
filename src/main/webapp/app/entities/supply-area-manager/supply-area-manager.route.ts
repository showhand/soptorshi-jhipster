import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ISupplyAreaManager, SupplyAreaManager } from 'app/shared/model/supply-area-manager.model';
import { SupplyAreaManagerService } from './supply-area-manager.service';
import { SupplyAreaManagerComponent } from './supply-area-manager.component';
import { SupplyAreaManagerDetailComponent } from './supply-area-manager-detail.component';
import { SupplyAreaManagerUpdateComponent } from './supply-area-manager-update.component';
import { SupplyAreaManagerDeletePopupComponent } from './supply-area-manager-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class SupplyAreaManagerResolve implements Resolve<ISupplyAreaManager> {
    constructor(private service: SupplyAreaManagerService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISupplyAreaManager> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SupplyAreaManager>) => response.ok),
                map((supplyAreaManager: HttpResponse<SupplyAreaManager>) => supplyAreaManager.body)
            );
        }
        return of(new SupplyAreaManager());
    }
}

export const supplyAreaManagerRoute: Routes = [
    {
        path: '',
        component: SupplyAreaManagerComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyAreaManagers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SupplyAreaManagerDetailComponent,
        resolve: {
            supplyAreaManager: SupplyAreaManagerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyAreaManagers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SupplyAreaManagerUpdateComponent,
        resolve: {
            supplyAreaManager: SupplyAreaManagerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyAreaManagers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SupplyAreaManagerUpdateComponent,
        resolve: {
            supplyAreaManager: SupplyAreaManagerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyAreaManagers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const supplyAreaManagerPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SupplyAreaManagerDeletePopupComponent,
        resolve: {
            supplyAreaManager: SupplyAreaManagerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyAreaManagers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
