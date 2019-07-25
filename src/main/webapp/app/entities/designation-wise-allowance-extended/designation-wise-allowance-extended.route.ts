import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import {
    DesignationWiseAllowanceComponent,
    DesignationWiseAllowanceDeletePopupComponent,
    DesignationWiseAllowanceDetailComponent,
    DesignationWiseAllowanceResolve,
    DesignationWiseAllowanceService,
    DesignationWiseAllowanceUpdateComponent
} from 'app/entities/designation-wise-allowance';
import { UserRouteAccessService } from 'app/core';
import { Injectable } from '@angular/core';
import { DesignationWiseAllowance, IDesignationWiseAllowance } from 'app/shared/model/designation-wise-allowance.model';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { DesignationWiseAllowanceExtendedComponent } from 'app/entities/designation-wise-allowance-extended/designation-wise-allowance-extended.component';
import { DesignationWiseAllowanceExtendedDetailsComponent } from 'app/entities/designation-wise-allowance-extended/designation-wise-allowance-extended-details.component';
import { DesignationWiseAllowanceExtendedUpdateComponent } from 'app/entities/designation-wise-allowance-extended/designation-wise-allowance-extended-update.component';

@Injectable({ providedIn: 'root' })
export class DesignationWiseAllowanceExtendedResolve extends DesignationWiseAllowanceResolve {
    constructor(public service: DesignationWiseAllowanceService) {
        super(service);
    }

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

export const designationWiseAllowanceExtendedRoute: Routes = [
    {
        path: '',
        component: DesignationWiseAllowanceExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DesignationWiseAllowances'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: DesignationWiseAllowanceExtendedDetailsComponent,
        resolve: {
            designationWiseAllowance: DesignationWiseAllowanceExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DesignationWiseAllowances'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: DesignationWiseAllowanceExtendedUpdateComponent,
        resolve: {
            designationWiseAllowance: DesignationWiseAllowanceExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DesignationWiseAllowances'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':designation/new',
        component: DesignationWiseAllowanceExtendedUpdateComponent,
        resolve: {
            designationWiseAllowance: DesignationWiseAllowanceExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DesignationWiseAllowances'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: DesignationWiseAllowanceExtendedUpdateComponent,
        resolve: {
            designationWiseAllowance: DesignationWiseAllowanceExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DesignationWiseAllowances'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const designationWiseAllowanceExtendedPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: DesignationWiseAllowanceDeletePopupComponent,
        resolve: {
            designationWiseAllowance: DesignationWiseAllowanceExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DesignationWiseAllowances'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
