import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Tax } from 'app/shared/model/tax.model';
import { TaxService } from './tax.service';
import { TaxComponent } from './tax.component';
import { TaxDetailComponent } from './tax-detail.component';
import { TaxUpdateComponent } from './tax-update.component';
import { TaxDeletePopupComponent } from './tax-delete-dialog.component';
import { ITax } from 'app/shared/model/tax.model';

@Injectable({ providedIn: 'root' })
export class TaxResolve implements Resolve<ITax> {
    constructor(private service: TaxService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITax> {
        const id = route.params['id'] ? route.params['id'] : null;
        const employeeLongId = route.params['employeeLongId'] ? route.params['employeeLongId'] : null;
        const employeeId = route.params['employeeId'] ? route.params['employeeId'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Tax>) => response.ok),
                map((tax: HttpResponse<Tax>) => tax.body)
            );
        } else if (employeeLongId) {
            const tax = new Tax();
            tax.employeeId = employeeLongId;
            return of(tax);
        } else if (employeeId) {
            const tax = new Tax();
            tax.employeeId = employeeId;
            return of(tax);
        }
        return of(new Tax());
    }
}

export const taxRoute: Routes = [
    {
        path: '',
        component: TaxComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Taxes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':employeeLongId/employee',
        component: TaxComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams,
            tax: TaxResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Taxes',
            breadcrumb: 'Employee Tax Information'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TaxDetailComponent,
        resolve: {
            tax: TaxResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Taxes',
            breadcrumb: 'Tax Details'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':employeeId/new',
        component: TaxUpdateComponent,
        resolve: {
            tax: TaxResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Taxes',
            breadcrumb: 'Employee New Tax Entry'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TaxUpdateComponent,
        resolve: {
            tax: TaxResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Taxes',
            breadcrumb: 'New Tax Entry'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TaxUpdateComponent,
        resolve: {
            tax: TaxResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Taxes',
            breadcrumb: 'Edit Tax Information'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const taxPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TaxDeletePopupComponent,
        resolve: {
            tax: TaxResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Taxes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
