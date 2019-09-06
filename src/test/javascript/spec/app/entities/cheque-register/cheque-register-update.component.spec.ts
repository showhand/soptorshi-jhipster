/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { ChequeRegisterUpdateComponent } from 'app/entities/cheque-register/cheque-register-update.component';
import { ChequeRegisterService } from 'app/entities/cheque-register/cheque-register.service';
import { ChequeRegister } from 'app/shared/model/cheque-register.model';

describe('Component Tests', () => {
    describe('ChequeRegister Management Update Component', () => {
        let comp: ChequeRegisterUpdateComponent;
        let fixture: ComponentFixture<ChequeRegisterUpdateComponent>;
        let service: ChequeRegisterService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [ChequeRegisterUpdateComponent]
            })
                .overrideTemplate(ChequeRegisterUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ChequeRegisterUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChequeRegisterService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ChequeRegister(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.chequeRegister = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ChequeRegister();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.chequeRegister = entity;
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
