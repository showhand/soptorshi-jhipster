/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { SalaryMessagesUpdateComponent } from 'app/entities/salary-messages/salary-messages-update.component';
import { SalaryMessagesService } from 'app/entities/salary-messages/salary-messages.service';
import { SalaryMessages } from 'app/shared/model/salary-messages.model';

describe('Component Tests', () => {
    describe('SalaryMessages Management Update Component', () => {
        let comp: SalaryMessagesUpdateComponent;
        let fixture: ComponentFixture<SalaryMessagesUpdateComponent>;
        let service: SalaryMessagesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SalaryMessagesUpdateComponent]
            })
                .overrideTemplate(SalaryMessagesUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SalaryMessagesUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SalaryMessagesService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new SalaryMessages(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.salaryMessages = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new SalaryMessages();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.salaryMessages = entity;
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
