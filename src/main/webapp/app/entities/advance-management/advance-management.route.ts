import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AdvanceManagement } from 'app/shared/model/advance-management.model';
import { AdvanceManagementService } from './advance-management.service';
import { AdvanceManagementComponent } from './advance-management.component';
import { AdvanceManagementDetailComponent } from './advance-management-detail.component';
import { AdvanceManagementUpdateComponent } from './advance-management-update.component';
import { AdvanceManagementDeletePopupComponent } from './advance-management-delete-dialog.component';
import { IAdvanceManagement } from 'app/shared/model/advance-management.model';

@Injectable({ providedIn: 'root' })
export class AdvanceManagementResolve implements Resolve<IAdvanceManagement> {
    constructor(private service: AdvanceManagementService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAdvanceManagement> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AdvanceManagement>) => response.ok),
                map((advanceManagement: HttpResponse<AdvanceManagement>) => advanceManagement.body)
            );
        }
        return of(new AdvanceManagement());
    }
}

export const advanceManagementRoute: Routes = [
    {
        path: '',
        component: AdvanceManagementComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AdvanceManagements'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AdvanceManagementDetailComponent,
        resolve: {
            advanceManagement: AdvanceManagementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AdvanceManagements'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AdvanceManagementUpdateComponent,
        resolve: {
            advanceManagement: AdvanceManagementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AdvanceManagements'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AdvanceManagementUpdateComponent,
        resolve: {
            advanceManagement: AdvanceManagementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AdvanceManagements'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const advanceManagementPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AdvanceManagementDeletePopupComponent,
        resolve: {
            advanceManagement: AdvanceManagementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AdvanceManagements'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
