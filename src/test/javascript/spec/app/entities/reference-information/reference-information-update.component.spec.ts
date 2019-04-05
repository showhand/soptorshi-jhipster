/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { ReferenceInformationUpdateComponent } from 'app/entities/reference-information/reference-information-update.component';
import { ReferenceInformationService } from 'app/entities/reference-information/reference-information.service';
import { ReferenceInformation } from 'app/shared/model/reference-information.model';

describe('Component Tests', () => {
    describe('ReferenceInformation Management Update Component', () => {
        let comp: ReferenceInformationUpdateComponent;
        let fixture: ComponentFixture<ReferenceInformationUpdateComponent>;
        let service: ReferenceInformationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [ReferenceInformationUpdateComponent]
            })
                .overrideTemplate(ReferenceInformationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ReferenceInformationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReferenceInformationService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ReferenceInformation(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.referenceInformation = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ReferenceInformation();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.referenceInformation = entity;
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
