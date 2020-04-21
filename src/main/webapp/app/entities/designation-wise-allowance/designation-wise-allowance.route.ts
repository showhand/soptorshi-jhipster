import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DesignationWiseAllowance } from 'app/shared/model/designation-wise-allowance.model';
import { DesignationWiseAllowanceService } from './designation-wise-allowance.service';
import { DesignationWiseAllowanceComponent } from './designation-wise-allowance.component';
import { DesignationWiseAllowanceDetailComponent } from './designation-wise-allowance-detail.component';
import { DesignationWiseAllowanceUpdateComponent } from './designation-wise-allowance-update.component';
import { DesignationWiseAllowanceDeletePopupComponent } from './designation-wise-allowance-delete-dialog.component';
import { IDesignationWiseAllowance } from 'app/shared/model/designation-wise-allowance.model';

@Injectable({ providedIn: 'root' })
export class DesignationWiseAllowanceResolve implements Resolve<IDesignationWiseAllowance> {
    constructor(public service: DesignationWiseAllowanceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDesignationWiseAllowance> {
        const id = route.params['id'] ? route.params['id'] : null;
        const designation = route.params['designation'] ? route.params['designation'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DesignationWiseAllowance>) => response.ok),
                map((designationWiseAllowance: HttpResponse<DesignationWiseAllowance>) => designationWiseAllowance.body)
            );
        } else if (designation) {
            const designationWiseAllowance = new DesignationWiseAllowance();
            designationWiseAllowance.designationId = designation;
            return of(designationWiseAllowance);
        }
        return of(new DesignationWiseAllowance());
    }
}

export const designationWiseAllowanceRoute: Routes = [
    {
        path: '',
        component: DesignationWiseAllowanceComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DesignationWiseAllowances'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: DesignationWiseAllowanceDetailComponent,
        resolve: {
            designationWiseAllowance: DesignationWiseAllowanceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DesignationWiseAllowances'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: DesignationWiseAllowanceUpdateComponent,
        resolve: {
            designationWiseAllowance: DesignationWiseAllowanceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DesignationWiseAllowances'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':designation/new',
        component: DesignationWiseAllowanceUpdateComponent,
        resolve: {
            designationWiseAllowance: DesignationWiseAllowanceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DesignationWiseAllowances'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: DesignationWiseAllowanceUpdateComponent,
        resolve: {
            designationWiseAllowance: DesignationWiseAllowanceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DesignationWiseAllowances'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const designationWiseAllowancePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: DesignationWiseAllowanceDeletePopupComponent,
        resolve: {
            designationWiseAllowance: DesignationWiseAllowanceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DesignationWiseAllowances'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
