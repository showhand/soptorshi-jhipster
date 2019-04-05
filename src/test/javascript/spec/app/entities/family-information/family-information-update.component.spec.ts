/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { FamilyInformationUpdateComponent } from 'app/entities/family-information/family-information-update.component';
import { FamilyInformationService } from 'app/entities/family-information/family-information.service';
import { FamilyInformation } from 'app/shared/model/family-information.model';

describe('Component Tests', () => {
    describe('FamilyInformation Management Update Component', () => {
        let comp: FamilyInformationUpdateComponent;
        let fixture: ComponentFixture<FamilyInformationUpdateComponent>;
        let service: FamilyInformationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [FamilyInformationUpdateComponent]
            })
                .overrideTemplate(FamilyInformationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FamilyInformationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FamilyInformationService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new FamilyInformation(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.familyInformation = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new FamilyInformation();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.familyInformation = entity;
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
