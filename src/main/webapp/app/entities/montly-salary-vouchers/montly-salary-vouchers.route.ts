import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MontlySalaryVouchers } from 'app/shared/model/montly-salary-vouchers.model';
import { MontlySalaryVouchersService } from './montly-salary-vouchers.service';
import { MontlySalaryVouchersComponent } from './montly-salary-vouchers.component';
import { MontlySalaryVouchersDetailComponent } from './montly-salary-vouchers-detail.component';
import { MontlySalaryVouchersUpdateComponent } from './montly-salary-vouchers-update.component';
import { MontlySalaryVouchersDeletePopupComponent } from './montly-salary-vouchers-delete-dialog.component';
import { IMontlySalaryVouchers } from 'app/shared/model/montly-salary-vouchers.model';

@Injectable({ providedIn: 'root' })
export class MontlySalaryVouchersResolve implements Resolve<IMontlySalaryVouchers> {
    constructor(private service: MontlySalaryVouchersService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMontlySalaryVouchers> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<MontlySalaryVouchers>) => response.ok),
                map((montlySalaryVouchers: HttpResponse<MontlySalaryVouchers>) => montlySalaryVouchers.body)
            );
        }
        return of(new MontlySalaryVouchers());
    }
}

export const montlySalaryVouchersRoute: Routes = [
    {
        path: '',
        component: MontlySalaryVouchersComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MontlySalaryVouchers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MontlySalaryVouchersDetailComponent,
        resolve: {
            montlySalaryVouchers: MontlySalaryVouchersResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MontlySalaryVouchers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MontlySalaryVouchersUpdateComponent,
        resolve: {
            montlySalaryVouchers: MontlySalaryVouchersResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MontlySalaryVouchers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MontlySalaryVouchersUpdateComponent,
        resolve: {
            montlySalaryVouchers: MontlySalaryVouchersResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MontlySalaryVouchers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const montlySalaryVouchersPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MontlySalaryVouchersDeletePopupComponent,
        resolve: {
            montlySalaryVouchers: MontlySalaryVouchersResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MontlySalaryVouchers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
