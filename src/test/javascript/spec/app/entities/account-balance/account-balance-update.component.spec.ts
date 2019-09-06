/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { AccountBalanceUpdateComponent } from 'app/entities/account-balance/account-balance-update.component';
import { AccountBalanceService } from 'app/entities/account-balance/account-balance.service';
import { AccountBalance } from 'app/shared/model/account-balance.model';

describe('Component Tests', () => {
    describe('AccountBalance Management Update Component', () => {
        let comp: AccountBalanceUpdateComponent;
        let fixture: ComponentFixture<AccountBalanceUpdateComponent>;
        let service: AccountBalanceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [AccountBalanceUpdateComponent]
            })
                .overrideTemplate(AccountBalanceUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AccountBalanceUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AccountBalanceService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new AccountBalance(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.accountBalance = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new AccountBalance();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.accountBalance = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
